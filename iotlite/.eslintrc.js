module.exports = {
  root: true,
  parser: 'vue-eslint-parser',

  // parser: 'babel-eslint',
  parserOptions: {
    // 
    "ecmaVersion": 7,
    "sourceType": "module"
  },
  env: {
    browser: true,
  },
  extends: [
    'plugin:vue/essential',
    'standard'
  ],
  // required to lint *.vue files
  plugins: [
    'vue'
  ],
  // add your custom rules here
  rules: {
    // allow async-await
    'generator-star-spacing': 'off',
    // allow debugger during development
    // 'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off'
    // "indent": ["error", 4],
    // "quotes": ["off"],
    // "semi": ["error", "always"],
    // "eqeqeq": "warn"
  },
  extends: [
    "prettier"
  ]
}