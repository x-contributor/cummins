(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('PreguntaDetailController', PreguntaDetailController);

    PreguntaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Pregunta', 'PreguntaSeccion'];

    function PreguntaDetailController($scope, $rootScope, $stateParams, entity, Pregunta, PreguntaSeccion) {
        var vm = this;

        vm.pregunta = entity;

        var unsubscribe = $rootScope.$on('cumminsApp:preguntaUpdate', function(event, result) {
            vm.pregunta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
