'use strict';

angular.module('itest.portal.common.directives')

    .directive('statusLine', function () {
        return {
            restrict: 'A',
            scope: {
                count: '='
            },
            link: function (scope, element) {
                element.context.style.width = 100/scope.count + '%';
                element.context.style.height = '5px';
            }
        };
    });
