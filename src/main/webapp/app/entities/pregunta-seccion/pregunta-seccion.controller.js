(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('PreguntaSeccionController', PreguntaSeccionController);

    PreguntaSeccionController.$inject = ['$scope', '$state', 'PreguntaSeccion'];

    function PreguntaSeccionController ($scope, $state, PreguntaSeccion) {
        var vm = this;
        
        vm.preguntaSeccions = [];

        loadAll();

        function loadAll() {
            PreguntaSeccion.query(function(result) {
                vm.preguntaSeccions = result;
            });
        }
    }
})();
