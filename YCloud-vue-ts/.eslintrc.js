module.exports = {
  env: {
    browser: true,
    es2021: true
  },
  extends: [
    "plugin:vue/vue3-essential",
    "plugin:vue/vue3-recommended",
    "standard-with-typescript"
  ],
  overrides: [
  ],
  parserOptions: {
    ecmaVersion: "latest",
    parser: "@typescript-eslint/parser",
    sourceType: "module"
  },
  plugins: [
    "vue", "@typescript-eslint"
  ],
  rules: {
    quotes: ["warn", "double"],
    semi: ["error", "always"],
    "space-before-function-paren": ["error", {
      anonymous: "never",
      named: "never",
      asyncArrow: "always"
    }],
    "prefer-const": 0,
    "vue/multi-word-component-names": ["error", {
      ignores: ["Login"]
    }]
  }
};
