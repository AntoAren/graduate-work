'use strict';

angular.module('itest.portal.common.directives')

    .directive('editButton', function(authSession, notifier, orderDialog, $state, testService) {
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
                    $state.go('edittest', {testId: scope.item.id});
                };

                scope.$watch('item', function() {
                    if (scope.item) {
                        scope.text = 'Изменить';
                        scope.class = 'btn-brand';
                        scope.visible = true;
                    }
                }, true);
            }
        };
    });
