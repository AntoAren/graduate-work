'use strict';

angular.module('itest.portal.common.directives')

    .directive('passingButton', function(authSession, notifier, orderDialog, $state) {
        return {
            restrict: 'E',
            replace: true,
            template: '<div disable-if="orderProcessing" class="order-btn-container">' +
            '<div class="btn" href="" ng-show="visible" ng-bind="text" ng-class="class"' +
            ' ng-click="openOrderDialog()"/></div>',
            scope: {
                item: '='
            },
            link: function(scope) {

                scope.openOrderDialog = function() {
                    var header = 'Вы действительно хотите пройти этот тест сейчас?';
                    var text = 'Вы не сможете пройти его еще раз позже.';

                    orderDialog.open(scope.item, header, text).then(function() {
                        $state.go('passingtest', {testId: item.id});
                    });
                };

                scope.$watch('item', function() {
                    if (scope.item) {
                        scope.text = 'Пройти';
                        scope.class = 'btn-brand';
                        scope.visible = true;
                    }
                }, true);
            }
        };
    });
