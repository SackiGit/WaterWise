  function loadActivityChart() {


    const chartElement = document.getElementById("activityChart")
    if(!chartElement){
        return;
    }

    const activityData = window.activityConsumption || [];
    const labels = activityData.map(row => row.activity);
    const values = activityData.map(row => row.totallitres);
      new Chart(chartElement, {
                type: "bar",
                data: {
                    labels: labels,

                    datasets: [{
                        label: "Consumption per activity",
                        data: values ,
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
                                label: function(context){
                                return `${context.parsed.y} L`;
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
                                display: false,
                                color: "#64748b",
                                maxRotation: 0,
                                autoSkip: false
                            }
                        },

                        y: {
                            beginAtZero: true,

                            title: {
                                display: true,
                                text: "Litres",
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
    const pieChartElement = document.getElementById("activityPieChart");

    if(!pieChartElement) return;
    const activities = window.activityConsumption || [];
    const pieLabels = activities.map(row => row.activity);
    const pieValues = activities.map(row => Number(row.totallitres));
    new Chart(pieChartElement, {
        type: "pie",

        data: {
            labels: pieLabels,
            datasets: [{
                label: "Water consumption",
                data: pieValues
            }]
        },
        options: {
            plugins: {
                tooltip: {
                    callbacks:{
                        label: function(context){
                            const values = context.dataset.data;
                            const total = values.reduce((sum,value) => sum + value, 0);
                            const percentage = (context.parsed / total) * 100;

                            return percentage.toFixed(1) + "%";
                        }
                    }
                }
            }
        }
    });
}


loadActivityChart();