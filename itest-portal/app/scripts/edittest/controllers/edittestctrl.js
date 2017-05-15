'use strict';

angular.module('itest.portal.edittest.controllers')

    .controller('EditTestCtrl', function($scope, $stateParams, categoryService, topicService, testService, $state){
        var loadCategories = function() {
            categoryService.getCategoriesForPublicTests().then(function(response) {
                var categories = response.list.map(function(category) {
                    return {
                        name: category.name,
                        value: category.id,
                        selected: category.id === $scope.test.categoryId
                    };
                });

                $scope.categoryOptions.items = $scope.categoryOptions.items.concat(categories);
            });
        };

        var loadTopics = function() {
            topicService.getTopicsForPublicTests().then(function(response) {
                var topics = response.list.map(function(topic) {
                    return {
                        name: topic.name,
                        value: topic.id,
                        selected: topic.id === $scope.test.topicId
                    };
                });

                $scope.topicOptions.items = $scope.topicOptions.items.concat(topics);
            });
        };

        var cleanEmptyItems = function() {
            var questions = [];
            $.each($scope.test.questions, function (firstIndex, currentQuestion) {
                var question = {
                    answers: []
                };
                $.each(currentQuestion.answers, function (secondIndex, answer) {
                    if (answer.text.trim() !== '') {
                        question.answers.push({
                            id: answer.id,
                            text: answer.text.trim(),
                            correct: answer.correct
                        });
                    }
                });
                if (currentQuestion.text.trim() !== '' || question.answers.length !== 0) {
                    if (question.answers.length === 0) {
                        question.answers.push({
                            id: 1,
                            text: '',
                            correct: true
                        });
                        question.answers.push({
                            id: 2,
                            text: '',
                            correct: false
                        });
                    }
                    question.id = currentQuestion.id;
                    question.text = currentQuestion.text.trim();
                    questions.push(question);
                }
            });
            if (questions.length === 0) {
                questions.push({
                    id: 1,
                    text: '',
                    answers: [
                        {
                            id: 1,
                            text: '',
                            correct: true
                        },
                        {
                            id: 2,
                            text: '',
                            correct: false
                        }
                    ]
                });
            }
            $scope.test.questions = questions;
        };

        var validateTest = function() {
            var numberCorrectAnswers;
            $scope.validationFailed = false;
            $.each($scope.test.questions, function (firstIndex, question) {
                if (question.text.trim() === '') {
                    $scope.validationFailed = true;
                }
                numberCorrectAnswers = 0;
                $.each(question.answers, function (secondIndex, answer) {
                    if (answer.text.trim() === '') {
                        $scope.validationFailed = true;
                    }
                    if (answer.correct) {
                        numberCorrectAnswers++;
                    }
                });
                if (numberCorrectAnswers === 0 || numberCorrectAnswers === question.answers.length) {
                    $scope.validationFailed = true;
                }
            });
            if ($scope.test.categoryId === 0 || $scope.test.topicId === 0) {
                $scope.validationFailed = true;
            }
        };

        var fillQuestionsCountOptions = function() {
            var currentPosition;
            $.each($scope.test.questions, function (index) {
                currentPosition = index + 1;
                $scope.questionsCountOptions.items.push(
                    {
                        name: currentPosition.toString(),
                        value: currentPosition,
                        selected: currentPosition === $scope.test.questionsNumber
                    }
                );
            })
        };

        var loadTest = function() {
            testService.getTest($stateParams.testId).then(
                function (response) {
                    $scope.test = {
                        testId: response.testId,
                        categoryId: response.categoryId,
                        topicId: response.topicId,
                        questionsNumber: response.questionsNumber,
                        privateTest: response.privateTest,
                        showCorrectAnswers: response.showCorrectAnswers,
                        questions: response.questions
                    };
                    fillQuestionsCountOptions();
                    loadCategories();
                    loadTopics();
            },  function () {

            });
        };

        $scope.questionsCountOptions = {
            title: 'Количество вопросов в тесте:',
            items: []
        };

        $scope.categoryOptions = {
            title: 'Категория:',
            items: [
                {name: 'Все категории', value: 0, selected: true}
            ]
        };

        $scope.topicOptions = {
            title: 'Тема:',
            items: [
                {name: 'Все темы', value: 0, selected: true}
            ]
        };

        $scope.addAnswer = function(id) {
            $.each($scope.test.questions, function(index, question) {
                if (question.id === id) {
                    question.answers.push({
                        id: -(question.answers.length + 1),
                        text: '',
                        correct: false
                    });
                }
            });
        };

        $scope.addQuestion = function() {
            $scope.test.questions.push({
                id: -($scope.test.questions.length + 1),
                text: '',
                answers: [
                    {
                        id: -1,
                        text: '',
                        correct: true
                    },
                    {
                        id: -2,
                        text: '',
                        correct: false
                    }
                ]
            });
            $scope.questionsCountOptions.items.push({
                name: ($scope.questionsCountOptions.items.length + 1).toString(),
                value: $scope.questionsCountOptions.items.length + 1,
                selected: false
            });
        };

        $scope.removeAnswer = function(questionId, answerId) {
            var answers = [];
            var index = 1;
            $.each($scope.test.questions, function(firstIndex, question){
                if (question.id === questionId) {
                    $.each(question.answers, function(secondIndex, answer) {
                        if (answer.id !== answerId) {
                            answers.push({
                                id: index,
                                text: answer.text,
                                correct: answer.correct
                            });
                            index++;
                        }
                    });
                    question.answers = answers;
                }
            });
        };

        $scope.removeQuestion = function(id) {
            var questions = [];
            var index = 1;
            $.each($scope.test.questions, function(firstIndex, question){
                if (question.id !== id) {
                    questions.push({
                        id: index,
                        text: question.text,
                        answers: question.answers
                    });
                    index++;
                }
            });

            $scope.test.questions = questions;

            if ($scope.questionsCountOptions.items[$scope.questionsCountOptions.items.length - 1].selected) {
                $scope.questionsCountOptions.items[$scope.questionsCountOptions.items.length - 2].selected = true;
            }
            $scope.questionsCountOptions.items.pop();
        };

        $scope.calculateCorrectAnswers = function(id) {
            var count = 0;
            $.each($scope.test.questions, function(firstIndex, question) {
                if (question.id === id) {
                    $.each(question.answers, function (secondIndex, answer) {
                        if (answer.correct) {
                            count++;
                        }
                    });
                }
            });
            return count;
        };

        $scope.editTest = function() {
            cleanEmptyItems();
            validateTest();
            if (!$scope.validationFailed) {
                testService.editTest($stateParams.testId, $scope.test).then(function () {
                    $state.go("createdbyme");
                });
            }
        };

        $scope.convertNumberToLetter = function(number) {
            return String.fromCharCode(97 + number);
        };

        loadTest();
    });
