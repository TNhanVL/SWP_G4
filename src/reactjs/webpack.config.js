const path = require("path");
const webpack = require('webpack')
const Dotenv = require('dotenv-webpack');
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
  entry: "./src/index.js", // Dẫn tới file index.js ta đã tạo
  output: {
    path: path.resolve(__dirname, 'build'),
    filename: 'public/reactjs/bundle.js',
    publicPath: '/',
    clean: true
  },
  devServer: {
    historyApiFallback: true
  },
  devtool: 'inline-source-map',
  module: {
    rules: [
      {
        test: /\.js$/, // Sẽ sử dụng babel-loader cho những file .js
        exclude: /node_modules/, // Loại trừ thư mục node_modules
        use: ["babel-loader"]
      },
      {
        test: /\.css$/, // Sử dụng style-loader, css-loader cho file .css
        use: ["style-loader", "css-loader"]
      },
      {
        test: /\.(png|svg|jpg|jpeg|gif|ico)$/,
        exclude: /node_modules/,
        use: ['file-loader?name=[name].[ext]'] // ?name=[name].[ext] is only necessary to preserve the original file name
      }
    ]
  },
  performance: {
    maxEntrypointSize: 5120000,
    maxAssetSize: 5120000
  },
  // Chứa các plugins sẽ cài đặt trong tương lai
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
      templateParameters: {
        PUBLIC_URL: '/',
      },
      favicon: "./public/public/assets/imgs/logo.png"
    }),
    new Dotenv()
  ]
};
