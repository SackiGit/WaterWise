  function loadTotChart() {


    const chartElement = document.getElementById("totalChart")
    if(!chartElement){
        return;
    }

    const totalData = window.totalData || [];
    const labels = totalData.map(row => row.datum);
    const values = totalData.map(row => row.totallitres);
      new Chart(chartElement, {
                type: "line",
                data: {
                    labels: labels,

                    datasets: [{
                        label: "Total consumption over time",
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
                                return `Consumption: ${context.parsed.y} L`;
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


}
loadTotChart();