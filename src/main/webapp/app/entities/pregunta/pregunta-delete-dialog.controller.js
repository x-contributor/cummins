(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('PreguntaDeleteController',PreguntaDeleteController);

    PreguntaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pregunta'];

    function PreguntaDeleteController($uibModalInstance, entity, Pregunta) {
        var vm = this;

        vm.pregunta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pregunta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
