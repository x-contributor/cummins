(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('PreguntaSeccionDetailController', PreguntaSeccionDetailController);

    PreguntaSeccionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'PreguntaSeccion', 'Pregunta'];

    function PreguntaSeccionDetailController($scope, $rootScope, $stateParams, entity, PreguntaSeccion, Pregunta) {
        var vm = this;

        vm.preguntaSeccion = entity;

        var unsubscribe = $rootScope.$on('cumminsApp:preguntaSeccionUpdate', function(event, result) {
            vm.preguntaSeccion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
