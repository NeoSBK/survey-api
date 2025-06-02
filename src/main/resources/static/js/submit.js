export async function submitSurvey(surveyData) {
    try {
        const response = await fetch('http://localhost:8080/v1/api/survey', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(surveyData),
        });

        if (!response.ok) {
            throw new Error('Submission failed.');
        }

        return true;
    } catch (err) {
        console.error('Submission error:', err);
        return false;
    }
}
