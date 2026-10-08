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