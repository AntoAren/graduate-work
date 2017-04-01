'use strict';

angular.module('itest.portal.data.services')

    .factory('answerService', function ($window, Restangular) {
        var restService = Restangular.one('answers');

        return {

            getAnswers: function (testId) {
                return restService.one(testId).get();
            }
        };
    });
