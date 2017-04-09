'use strict';

angular.module('itest.portal.testpassing.controllers')

    .controller('TestPassingCtrl', function ($scope, testService, $stateParams, answerService) {
        var matchAnswersToQuestions = function () {
            $.each($scope.answers, function (answerIndex, answer) {
                $.each($scope.test.questions, function (questionIndex, question) {
                    if (answer.questionId === question.id) {
                        $.each(question.answers, function (answerSecondIndex, answerSecond) {
                            if (answer.answerIds.indexOf(answerSecond.id) !== -1) {
                                answerSecond.selected = true;
                            }
                        });
                    }
                });
            });
        };

        var getTest = function () {
            testService.getTestForPassing($stateParams.testId).then(function (response) {
                $scope.test = response;
                $scope.activeQuestion = $scope.test.questions[0];
                getAnswers();
            }, function () {

            });
        };

        var getAnswers = function () {
            answerService.getAnswers($stateParams.testId).then(function (response) {
                $scope.answers = response.list;
                matchAnswersToQuestions();
                $scope.loading = false;
            }, function () {

            });
        };

        $scope.hasPreviousQuestion = function () {
            var result = true;
            $.each($scope.test.questions, function (questionIndex, question) {
                if (question.id === $scope.activeQuestion.id) {
                    result = questionIndex !== 0;
                }
            });
            return result;
        };

        $scope.hasNextQuestion = function () {
            var result = true;
            $.each($scope.test.questions, function (questionIndex, question) {
                if (question.id === $scope.activeQuestion.id) {
                    result = questionIndex !== ($scope.test.questions.length - 1);
                }
            });
            return result;
        };

        $scope.loading = true;

        $scope.nextQuestion = function () {
            var index = 0;
            $.each($scope.test.questions, function (questionIndex, question) {
                if (question.id === $scope.activeQuestion.id) {
                    index = questionIndex;
                }
            });
            $scope.activeQuestion = $scope.test.questions[index + 1];
        };

        $scope.previousQuestion = function () {
            var index = 0;
            $.each($scope.test.questions, function (questionIndex, question) {
                if (question.id === $scope.activeQuestion.id) {
                    index = questionIndex;
                }
            });
            $scope.activeQuestion = $scope.test.questions[index - 1];
        };

        $scope.sendAnswer = function (questionId, answerId) {
            answerService.submitAnswer($scope.test.id, questionId, answerId).then(function (response) {

            });
        };

        getTest();
    });