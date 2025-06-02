import { fetchSurveyStatistics } from './api.js';
import { renderSurveyResults } from './render.js';

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const data = await fetchSurveyStatistics();
        renderSurveyResults(data);
    } catch (error) {
        console.error('Error loading survey results:', error);
        document.getElementById('loadingMessage').style.display = 'none';
        document.getElementById('noDataMessage').style.display = 'block';
        document.getElementById('surveyData').style.display = 'none';
    }
});
