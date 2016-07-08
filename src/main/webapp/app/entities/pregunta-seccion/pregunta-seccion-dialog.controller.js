(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('PreguntaSeccionDialogController', PreguntaSeccionDialogController);

    PreguntaSeccionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PreguntaSeccion', 'Pregunta'];

    function PreguntaSeccionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PreguntaSeccion, Pregunta) {
        var vm = this;

        vm.preguntaSeccion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.preguntas = Pregunta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.preguntaSeccion.id !== null) {
                PreguntaSeccion.update(vm.preguntaSeccion, onSaveSuccess, onSaveError);
            } else {
                PreguntaSeccion.save(vm.preguntaSeccion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cumminsApp:preguntaSeccionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
