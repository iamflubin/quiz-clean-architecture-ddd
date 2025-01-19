/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,ts}'],
  theme: {
    fontFamily: {
      sans: ['DM Sans', 'sans-serif'],
    },
    extend: {},
  },
  plugins: [require('daisyui')],
  daisyui: {
    themes: [
      {
        mytheme: {
          primary: '#f7cb15',
          secondary: '#000000',
          accent: '#000000',
          neutral: '#000000',
          'base-100': '#ffffff',
        },
      },
    ],
  },
};
