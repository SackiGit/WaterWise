function loadMunChart(){


 const chartElement = document.getElementById("indexChart")
    if(!chartElement){
        return;
    }

    const avgData = window.avgData || [];


    const labels = [
                           "January", "February", "March", "April",
                           "May", "June", "July", "August",
                           "September", "October", "November", "December"
                       ];
    const values = avgData.map(row => row.avgcon);
      new Chart(chartElement, {
                type: "bar",
                data: {
                    labels: labels,

                    datasets: [{
                        label: "Average consumption in your municipality",
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
                    grouped: true,

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
            }
loadMunChart();