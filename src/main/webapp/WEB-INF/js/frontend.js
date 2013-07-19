function replotGraph() {
    var predatorsInitialAmount = document.getElementsByName('predatorsInitialDensity')[0].value;
    var preyInitialAmount = document.getElementsByName('preyInitialDensity')[0].value;

    var alpha = document.getElementsByName('preyPopulationIncreaseRate')[0].value;
    var beta = document.getElementsByName('predationRateCoefficient')[0].value;
    var gamma = document.getElementsByName('predatorsMortalityRate')[0].value;
    var delta = document.getElementsByName('predatorsReproductionRate')[0].value;

    var t0 = document.getElementsByName('timeIntervalBegin')[0].value;
    var t1 = document.getElementsByName('timeIntervalEnd')[0].value;
    var dt = document.getElementsByName('dt')[0].value;

    var dataArray = [];

    $.ajax({
        url:'/compute/' + alpha + '/' + beta + '/' + gamma + '/' + delta + '/' + t0 +'/' + t1 +'/' +
            dt + '/' + predatorsInitialAmount + '/' + preyInitialAmount + '',
        success: function (data) {
            dataArray = data;
        }, type: 'POST', async: false
    });

    plotAGraph(dataArray['predators'], dataArray['prey'], parseFloat(t0), parseFloat(t1), parseFloat(dt));
}