'use strict';

angular.module('itest.portal.data.services')

    .factory('categoryService', function (Restangular) {
        var restService = Restangular.one('categories');

        return {
            getCategoriesForPublicTests: function () {
                return restService.get();
            }
        };
    });
