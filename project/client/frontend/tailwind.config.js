/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      backgroundImage: {
        'second-page': "url('./assets/images/main_page/main_bg_img.png')",
        'kakao-login-btn-lg-wd':
          "url('./assets/images/kakao_login/kakao_login_large_wide.png')",
        'kakao-login-btn-lg-nr':
          "url('./assets/images/kakao_login/kakao_login_large_narrow.png')",
        'kakao-login-btn-md-wd':
          "url('./assets/images/kakao_login/kakao_login_medium_wide.png')",
        'kakao-login-btn-md-nr':
          "url('./assets/images/kakao_login/kakao_login_medium_narrow.png')",
      },
    },
    colors: {
      transparent: 'transparent',
      current: 'currentColor',
      BACKGROUND: {
        50: '#ffffff',
        100: '#efefef',
        200: '#dcdcdc',
        300: '#bdbdbd',
        400: '#989898',
        500: '#7c7c7c',
        600: '#656565',
        700: '#525252',
        800: '#464646',
        900: '#3d3d3d',
        950: '#292929',
      },
      FONT: {
        50: '#f6f6f6',
        100: '#e7e7e7',
        200: '#d1d1d1',
        300: '#b0b0b0',
        400: '#888888',
        500: '#6d6d6d',
        600: '#5d5d5d',
        700: '#4f4f4f',
        800: '#454545',
        900: '#3d3d3d',
        950: '#000000',
      },
      LOGO: {
        50: '#fff1f1',
        100: '#ffe0e0',
        200: '#ffc6c6',
        300: '#ff9e9e',
        400: '#ff6666',
        500: '#fd4444',
        600: '#eb1717',
        700: '#c60f0f',
        800: '#a31111',
        900: '#871515',
        950: '#4a0505',
      },
      BUTTON2: {
        50: '#f7f6f5',
        100: '#edeae8',
        200: '#d9d4cf',
        300: '#c1b8b0',
        400: '#a79990',
        500: '#95847a',
        600: '#88766e',
        700: '#72615c',
        800: '#5e514e',
        900: '#4d4441',
        950: '#292321',
      },
      BUTTON1: {
        50: '#f6f5f5',
        100: '#e7e6e6',
        200: '#d1d0d0',
        300: '#b2afae',
        400: '#8a8686',
        500: '#7c7877',
        600: '#5f5c5b',
        700: '#504e4e',
        800: '#464444',
        900: '#3d3c3c',
        950: '#272625',
      },
      SECONDARY: {
        50: '#f3f8f7',
        100: '#dfeeed',
        200: '#c4dedc',
        300: '#abd0ce',
        400: '#69a7a4',
        500: '#4e8c8a',
        600: '#437677',
        700: '#3b6163',
        800: '#365254',
        900: '#314648',
        950: '#1d2d2f',
      },
      MAIN: {
        50: '#f9f5f3',
        100: '#f035de',
        200: '#e4cfc4',
        300: '#d2b09f',
        400: '#bf8b78',
        500: '#b2705d',
        600: '#a55f51',
        700: '#894d45',
        800: '#6f403d',
        900: '#5b3633',
        950: '#301b1a ',
      },
    },
  },
  plugins: [],
};
