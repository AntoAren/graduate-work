'use strict';

angular.module('itest.portal.data.services')

    .factory('answerService', function ($window, Restangular) {
        var restService = Restangular.one('answers');

        return {

            getAnswers: function (testId) {
                return restService.one(testId).get();
            },

            submitAnswer: function (testId, params) {
                return restService.one(testId.toString()).customPOST(params);
            }
        };
    });
