const canvas = document.getElementById("rainChart");

const rainChart = new Chart(canvas, {
    type: "bar",

    data: {
        labels: ["12:00", "15:00", "18:00", "21:00"],

        datasets: [{
            label: "Rainfall (mm)",
            data: [0, 1.2, 3.5, 0.8],
            backgroundColor: "#36a2eb"
        }]
    },

    options: {
        responsive: true,

        scales: {
            x: {
                title: {
                    display: true,
                    text: "Time"
                }
            },

            y: {
                beginAtZero: true,

                title: {
                    display: true,
                    text: "Rainfall (mm)"
                }
            }
        }
    }
});