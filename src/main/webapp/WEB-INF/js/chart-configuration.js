function plotAGraph(arrayPredators, arrayPrey, t0, t1, dt) {
    $('#graphics').highcharts({
        chart: {
            zoomType: 'x',
            spacingRight: 20
        },
        title: {
            text: 'Lotka-Volterra History Simulation'
        },
        subtitle: {
            text: document.ontouchstart === undefined ?
                'Click and drag in the plot area to zoom in' :
                'Drag your finger over the plot to zoom in'
        },
        xAxis: {
            type: 'linear',
            title: {
                text: 'Time Units'
            },
            min:t0,
            max:t1
        },
        yAxis: {
            title: {
                text: 'Population'
            }
        },
        tooltip: {
            shared: true
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                lineWidth: 1,
                marker: {
                    enabled: false
                },
                shadow: false,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },

        series: [{
            type: 'area',
            name: 'Predators',
            data: arrayPredators,
            pointStart:t0,
            pointInterval: dt
        },{
            type: 'area',
            name: 'Prey',
            data: arrayPrey,
            pointStart:t0,
            pointInterval: dt
        }]
    });
}

$(plotAGraph());