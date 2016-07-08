(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('RespuestaDialogController', RespuestaDialogController);

    RespuestaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Respuesta', 'Pregunta', 'Proyecto'];

    function RespuestaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Respuesta, Pregunta, Proyecto) {
        var vm = this;

        vm.respuesta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.preguntas = Pregunta.query({filter: 'respuesta-is-null'});
        $q.all([vm.respuesta.$promise, vm.preguntas.$promise]).then(function() {
            if (!vm.respuesta.pregunta || !vm.respuesta.pregunta.id) {
                return $q.reject();
            }
            return Pregunta.get({id : vm.respuesta.pregunta.id}).$promise;
        }).then(function(pregunta) {
            vm.preguntas.push(pregunta);
        });
        vm.proyectos = Proyecto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.respuesta.id !== null) {
                Respuesta.update(vm.respuesta, onSaveSuccess, onSaveError);
            } else {
                Respuesta.save(vm.respuesta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cumminsApp:respuestaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
