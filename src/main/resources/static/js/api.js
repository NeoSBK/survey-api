export async function fetchSurveyStatistics() {
    const response = await fetch('http://localhost:8080/v1/api/survey/statistics');

    if (!response.ok) {
        throw new Error('Failed to fetch survey results');
    }

    return await response.json();
}
