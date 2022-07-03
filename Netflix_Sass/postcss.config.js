module.exports = {
  plugins: [
    require('postcss-combine-media-query'),
    require('postcss-csso')({
      restructure: false
    })
  ]
}