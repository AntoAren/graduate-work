'use strict';

angular.module('itest.portal.common.directives')

    .directive('deleteButton', function(authSession, notifier, orderDialog, $state, testService) {
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
                    var header = 'Вы действительно хотите удалить этот тест?';
                    var text = 'Вы не сможете восстановить его.';

                    orderDialog.open(scope.item, header, text).then(function() {
                        testService.deleteTest(scope.item.id).then(function () {
                            $state.go('createdbyme');
                        });
                    });
                };

                scope.$watch('item', function() {
                    if (scope.item) {
                        scope.text = 'Удалить';
                        scope.class = 'btn-brand';
                        scope.visible = true;
                    }
                }, true);
            }
        };
    });
