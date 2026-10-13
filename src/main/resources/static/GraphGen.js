// Open-Meteo dates use YYYY-MM-DD.
// Parse as a local date so the displayed day does not shift.
function parseForecastDate(dateString) {
    const [year, month, day] = dateString.split("-").map(Number);
    return new Date(year, month - 1, day, 12);
}

async function loadRainForecast() {
    const status = document.getElementById("forecast-status");
    const content = document.getElementById("forecast-content");

    try {
        // Calls RainForecastController.
        const response = await fetch("/api/rain-forecast", {
            headers: {
                Accept: "application/json"
            }
        });

        if (!response.ok) {
            throw new Error(`Forecast request failed: ${response.status}`);
        }

        // Each object contains "date" and "rain" from RainData.
        const forecast = await response.json();

        if (!Array.isArray(forecast) || forecast.length === 0) {
            throw new Error("No forecast data received.");
        }

        const invalidData = forecast.some(day =>
            !day ||
            typeof day.date !== "string" ||
            !/^\d{4}-\d{2}-\d{2}$/.test(day.date) ||
            !Number.isFinite(day.rain) ||
            day.rain < 0
        );

        if (invalidData) {
            throw new Error("Invalid forecast data received.");
        }

        forecast.sort((a, b) => a.date.localeCompare(b.date));

        // Calculate the summary cards.
        const totalRain = forecast.reduce(
            (total, day) => total + day.rain,
            0
        );

        const wettestDay = forecast.reduce((wettest, day) =>
            day.rain > wettest.rain ? day : wettest
        );

        document.getElementById("total-rain").textContent =
            `${totalRain.toFixed(1)} mm`;

        document.getElementById("wettest-day").textContent =
            totalRain === 0
                ? "No rain expected"
                : parseForecastDate(wettestDay.date).toLocaleDateString(
                    "en-GB",
                    { weekday: "long" }
                );

        document.getElementById("wettest-amount").textContent =
            `${wettestDay.rain.toFixed(1)} mm expected`;

        // Two-line labels: weekday above, date below.
        const labels = forecast.map(day => {
            const date = parseForecastDate(day.date);

            return [
                date.toLocaleDateString("en-GB", {
                    weekday: "short"
                }),
                date.toLocaleDateString("en-GB", {
                    day: "numeric",
                    month: "short"
                })
            ];
        });

        // Show the container before Chart.js measures it.
        content.hidden = false;

        new Chart(document.getElementById("rainChart"), {
            type: "bar",

            data: {
                labels: labels,

                datasets: [{
                    label: "Expected rainfall",
                    data: forecast.map(day => day.rain),
                    backgroundColor: "#38bdf8",
                    hoverBackgroundColor: "#0284c7",
                    borderRadius: 6,
                    borderSkipped: "bottom",
                    maxBarThickness: 56
                }]
            },

            options: {
                responsive: true,
                maintainAspectRatio: false,

                // Allows hovering anywhere above a day's position,
                // including days with zero rainfall.
                interaction: {
                    mode: "index",
                    intersect: false
                },

                plugins: {
                    legend: {
                        display: false
                    },

                    tooltip: {
                        displayColors: false,
                        backgroundColor: "#0f172a",
                        padding: 12,

                        callbacks: {
                            title: function (items) {
                                const day = forecast[items[0].dataIndex];

                                return parseForecastDate(day.date)
                                    .toLocaleDateString("en-GB", {
                                        weekday: "long",
                                        day: "numeric",
                                        month: "long"
                                    });
                            },

                            label: function (context) {
                                const amount = context.parsed.y.toFixed(1);
                                return `Expected rainfall: ${amount} mm`;
                            }
                        }
                    }
                },

                scales: {
                    x: {
                        grid: {
                            display: false
                        },
                        border: {
                            display: false
                        },
                        ticks: {
                            color: "#64748b",
                            maxRotation: 0,
                            autoSkip: false
                        }
                    },

                    y: {
                        beginAtZero: true,
                        suggestedMax: 5,

                        title: {
                            display: true,
                            text: "Rainfall (mm)",
                            color: "#64748b"
                        },
                        grid: {
                            color: "#f1f5f9"
                        },
                        border: {
                            display: false
                        },
                        ticks: {
                            color: "#64748b"
                        }
                    }
                }
            }
        });

        status.textContent = "";
        status.hidden = true;

    } catch (error) {
        console.error("Could not load rainfall forecast:", error);

        content.hidden = true;
        status.hidden = false;
        status.textContent =
            "Could not load the forecast. Please try refreshing the page.";
    }
}
loadRainForecast();

async function loadGroundwaterBox() {
    const status = document.getElementById("groundwater-status");
    const content = document.getElementById("groundwater-content");
    const card = document.querySelector(".groundwater-card");

    // The shared script might also be loaded on other pages.
    if (!status || !content || !card) {
        return;
    }

    try {
        const response = await fetch("/api/groundwater/current", {
            headers: {
                Accept: "application/json"
            }
        });

        if (!response.ok) {
            throw new Error(
                `Groundwater request failed: ${response.status}`
            );
        }
        const data = await response.json();
        //data.level = 50; // Temporary test value

// SGU can return a date such as "2026-10-11Z".
// Remove the trailing timezone marker for local date formatting.
        const dateString =
            typeof data?.date === "string"
                ? data.date.replace(/Z$/, "")
                : "";

        if (
            !data ||
            !Number.isInteger(data.areaId) ||
            !Number.isInteger(data.level) ||
            data.level < 0 ||
            data.level > 100 ||
            !/^\d{4}-\d{2}-\d{2}$/.test(dateString)
        ) {
            throw new Error("Invalid groundwater data received.");
        }

        const [year, month, day] = dateString.split("-").map(Number);
        const date = new Date(year, month - 1, day, 12);

        if (
            date.getFullYear() !== year ||
            date.getMonth() !== month - 1 ||
            date.getDate() !== day
        ) {
            throw new Error("Invalid groundwater date received.");
        }

        let state;
        let label;
        let message;

        if (data.level < 25) {
            state = "low";
            label = "Low groundwater";
            message =
                "Groundwater is low. Please limit unnecessary water use.";

        } else if (data.level < 75) {
            state = "normal";
            label = "Normal groundwater";
            message =
                "Groundwater is within the normal range. " +
                "Use water responsibly.";

        } else {
            state = "high";
            label = "High groundwater";
            message =
                "Groundwater is high. Continue using water responsibly.";
        }

        card.dataset.state = state;

        document.getElementById("groundwater-value").textContent =
            data.level;

        document.getElementById("groundwater-badge").textContent =
            label;

        document.getElementById("groundwater-message").textContent =
            message;

        const formattedDate = date.toLocaleDateString("en-GB", {
            day: "numeric",
            month: "long",
            year: "numeric"
        });

        document.getElementById("groundwater-updated").textContent =
            `Latest available estimate: ${formattedDate} `;

        content.hidden = false;
        status.hidden = true;

    } catch (error) {
        console.error("Could not load groundwater data:", error);

        content.hidden = true;
        status.hidden = false;
        status.textContent =
            "Groundwater data is currently unavailable. " +
            "Please try refreshing the page.";
    }
}

// Works whether the script loads before or after the HTML.
if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", loadGroundwaterBox);
} else {
    loadGroundwaterBox();
}