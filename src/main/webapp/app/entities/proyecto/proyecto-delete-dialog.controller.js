(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ProyectoDeleteController',ProyectoDeleteController);

    ProyectoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Proyecto'];

    function ProyectoDeleteController($uibModalInstance, entity, Proyecto) {
        var vm = this;

        vm.proyecto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Proyecto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
