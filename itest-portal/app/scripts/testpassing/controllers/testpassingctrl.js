'use strict';

angular.module('itest.portal.testpassing.controllers')

    .controller('TestPassingCtrl', function ($scope, testService, $stateParams, answerService) {
        var matchAnswersToQuestions = function () {
            $.each($scope.answers, function (answerIndex, answer) {
                $.each($scope.test.questions, function (questionIndex, question) {
                    if (answer.questionId === question.id) {
                        question.answerIds = answer.answerIds;
                    }
                });
            });
        };

        var getTest = function () {
            testService.getTestForPassing($stateParams.testId).then(function (response) {
                $scope.test = response;
                $scope.test.questions[0].active = true;
                getAnswers();
            }, function () {

            });
        };

        var getAnswers = function () {
            answerService.getAnswers($stateParams.testId).then(function (response) {
                $scope.answers = response.list;
                matchAnswersToQuestions();
            }, function () {

            });
        };

        getTest();
    });