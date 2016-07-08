(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('PreguntaController', PreguntaController);

    PreguntaController.$inject = ['$scope', '$state', 'Pregunta'];

    function PreguntaController ($scope, $state, Pregunta) {
        var vm = this;
        
        vm.preguntas = [];

        loadAll();

        function loadAll() {
            Pregunta.query(function(result) {
                vm.preguntas = result;
            });
        }
    }
})();
