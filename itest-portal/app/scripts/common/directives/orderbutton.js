'use strict';

angular.module('itest.portal.common.directives')

.directive('orderButton', function(authSession, notifier, orderDialog, testService) {
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
                if (!scope.item.added) {
                    var header = 'Вы действительно хотите добавить этот тест?';
                    var text = 'Вы сможете пройти его на странице "Назначенные мне".';

                    orderDialog.open(scope.item, header, text).then(function() {
                        scope.orderProcessing = true;

                        testService.addTestToMe(scope.item.id).then(function () {
                            scope.item.added = true;
                            notifier.success('Тест был успешно добавлен.');
                        }).finally(function() {
                            scope.orderProcessing = false;
                        });
                    });
                }
            };

            scope.$watch('item', function() {
                if (scope.item) {

                    if (!scope.item.added) {
                        scope.text = 'Добавить';
                        scope.class = 'btn-brand';
                        scope.visible = true;
                    } else {
                        scope.text = 'Добавлен';
                        scope.class = 'btn-green pointer-disabled';
                        scope.visible = true;
                    }
                }
            }, true);
        }
    };
});
