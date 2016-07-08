(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ProyectoConcursoDeleteController',ProyectoConcursoDeleteController);

    ProyectoConcursoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProyectoConcurso'];

    function ProyectoConcursoDeleteController($uibModalInstance, entity, ProyectoConcurso) {
        var vm = this;

        vm.proyectoConcurso = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProyectoConcurso.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
