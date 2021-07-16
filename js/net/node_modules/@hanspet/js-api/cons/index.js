const Values = {
    titles: [
      "Alhaji",
      "Alhaja",
      "ARC.",
      "Canon",
      "Chief",
      "Deacon",
      "Dr.",
      "Engr",
      "Evangelist",
      "Hajiya",
      "Imam",
      "Mr.",
      "Mrs.",
      "Prof.",
      "Prophet",
      "REV",
      "VEN",
    ],
  
    securityQuestions: [
      "What was the name of your primary school?",
      "In what city or town does your nearest sibling live?",
      "What time of the day were you born? (hh:mm)",
      "What is your pet’s name?",
      "In what year was your father born?",
      "What is your favorite?",
      "Car registration number?",
      "What time of the day was your first child born?",
      "What is the first name of your best friend in high school?",
      "In what town or city did you meet your spouse or partner?",
      "What is the middle name of your oldest child?",
      "What is your spouse or partner's mother's maiden name?",
      "In what town or city did your parents meet?",
      "What was your childhood nickname?",
      "What is your oldest sibling’s birthday month and year? (e.g., January 1900)",
      "What is the name of the place your wedding reception was held?",
      "What is the name of a college you applied to but didn't attend?",
    ],
  
    getMonthDays: () => {
      return [
        { month: "January", days: 31, mNo: 1 },
        { month: "February", days: 29, mNo: 2 },
        { month: "March", days: 31, mNo: 3 },
        { month: "April", days: 30, mNo: 4 },
        { month: "May", days: 31, mNo: 5 },
        { month: "June", days: 30, mNo: 6 },
        { month: "July", days: 31, mNo: 7 },
        { month: "August", days: 31, mNo: 8 },
        { month: "September", days: 30, mNo: 9 },
        { month: "October", days: 31, mNo: 10 },
        { month: "November", days: 30, mNo: 11 },
        { month: "December", days: 31, mNo: 12 }
      ]
    },
  };

  module.exports = Values;