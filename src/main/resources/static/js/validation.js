export function validateForm(data) {
    const { fullName, email, dob, contact, q1, q2, q3, q4 } = data;

    if (!fullName || !email || !dob || !contact) {
        return "Please fill in all required fields.";
    }

    const birthDate = new Date(dob);
    const today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    if (age < 5 || age > 120) {
        return "Age must be between 5 and 120 years.";
    }

    if (!q1 || !q2 || !q3 || !q4) {
        return "Please select a rating for all four questions.";
    }

    return null;
}
