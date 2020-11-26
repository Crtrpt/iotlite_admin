import EmberRouter from '@ember/routing/router';
import config from 'iotlite/config/environment';

export default class Router extends EmberRouter {
  location = config.locationType;
  rootURL = config.rootURL;
}

Router.map(function() {
  this.route('login');
  this.route('product');
  this.route('device');
  this.route('gateway');
  this.route('user');
  this.route('organization');
  this.route('devicewarehouse');
  this.route('product_develop');
  this.route('device_develop');
  this.route('product_center');
});
