import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Route | product_center', function(hooks) {
  setupTest(hooks);

  test('it exists', function(assert) {
    let route = this.owner.lookup('route:product-center');
    assert.ok(route);
  });
});
