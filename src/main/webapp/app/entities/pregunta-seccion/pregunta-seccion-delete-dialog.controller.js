(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('PreguntaSeccionDeleteController',PreguntaSeccionDeleteController);

    PreguntaSeccionDeleteController.$inject = ['$uibModalInstance', 'entity', 'PreguntaSeccion'];

    function PreguntaSeccionDeleteController($uibModalInstance, entity, PreguntaSeccion) {
        var vm = this;

        vm.preguntaSeccion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PreguntaSeccion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
