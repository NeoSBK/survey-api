import { validateForm } from './validation.js';
import { submitSurvey } from './submit.js';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('surveyForm');

    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        const formData = new FormData(form);

        const data = {
            fullName: formData.get('fullName')?.trim(),
            email: formData.get('email')?.trim(),
            dob: formData.get('dob'),
            contact: formData.get('contact')?.trim(),
            q1: formData.get('q1'),
            q2: formData.get('q2'),
            q3: formData.get('q3'),
            q4: formData.get('q4'),
            favouriteFoods: formData.getAll('food')
        };

        const error = validateForm(data);

        if (error) {
            alert(error);
            return;
        }

        // Mapping
        const success = await submitSurvey({
            fullName: data.fullName,
            email: data.email,
            dateOfBirth: data.dob,
            contactNumber: data.contact,
            favouriteFoods: data.favouriteFoods,
            moviesRating: Number(data.q1),
            radioRating: Number(data.q2),
            eatOutRating: Number(data.q3),
            tvRating: Number(data.q4)
        });

        if (success) {
            alert('Survey submitted successfully!');
            form.reset();
        } else {
            alert('Error submitting survey, Check if all Fields Are Filled Properly.');
        }
    });
});
