export function renderSurveyResults(data) {
    const loading = document.getElementById('loadingMessage');
    const noData = document.getElementById('noDataMessage');
    const survey = document.getElementById('surveyData');

    loading.style.display = 'none';

    if (!data || data.totalSurveys === 0) {
        noData.style.display = 'block';
        survey.style.display = 'none';
        return;
    }

    noData.style.display = 'none';
    survey.style.display = 'block';

    document.getElementById('totalSurveys').textContent = data.totalSurveys || 0;
    document.getElementById('averageAge').textContent = data.averageAge ? `${data.averageAge} years` : '-';
    document.getElementById('oldestAge').textContent = data.oldestAge ? `${data.oldestAge} years` : '-';
    document.getElementById('youngestAge').textContent = data.youngestAge ? `${data.youngestAge} years` : '-';

    document.getElementById('pizzaPercentage').textContent = data.pizzaLoversPercentage ? `${data.pizzaLoversPercentage}%` : '0%';
    document.getElementById('pastaPercentage').textContent = data.pastaLoversPercentage ? `${data.pastaLoversPercentage}%` : '0%';
    document.getElementById('papWorsPercentage').textContent = data.papWorsLoversPercentage ? `${data.papWorsLoversPercentage}%` : '0%';

    document.getElementById('moviesPercentage').textContent = data.movieLoversAverageRating ? `${data.movieLoversAverageRating}%` : '0%';
    document.getElementById('radioPercentage').textContent = data.radioLoversAverageRating ? `${data.radioLoversAverageRating}%` : '0%';
    document.getElementById('eatOutPercentage').textContent = data.eatOutLoversAverageRating ? `${data.eatOutLoversAverageRating}%` : '0%';
    document.getElementById('tvPercentage').textContent = data.tvLoversAverageRating ? `${data.tvLoversAverageRating}%` : '0%';
}
