(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ProyectoConcursoController', ProyectoConcursoController);

    ProyectoConcursoController.$inject = ['$scope', '$state', 'ProyectoConcurso'];

    function ProyectoConcursoController ($scope, $state, ProyectoConcurso) {
        var vm = this;
        
        vm.proyectoConcursos = [];

        loadAll();

        function loadAll() {
            ProyectoConcurso.query(function(result) {
                vm.proyectoConcursos = result;
            });
        }
    }
})();
