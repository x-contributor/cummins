(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('RespuestaDeleteController',RespuestaDeleteController);

    RespuestaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Respuesta'];

    function RespuestaDeleteController($uibModalInstance, entity, Respuesta) {
        var vm = this;

        vm.respuesta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Respuesta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
