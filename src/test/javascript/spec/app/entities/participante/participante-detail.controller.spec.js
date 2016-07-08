'use strict';

describe('Controller Tests', function() {

    describe('Participante Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockParticipante, MockProyecto, MockContacto, MockParticipanteDescripcion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockParticipante = jasmine.createSpy('MockParticipante');
            MockProyecto = jasmine.createSpy('MockProyecto');
            MockContacto = jasmine.createSpy('MockContacto');
            MockParticipanteDescripcion = jasmine.createSpy('MockParticipanteDescripcion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Participante': MockParticipante,
                'Proyecto': MockProyecto,
                'Contacto': MockContacto,
                'ParticipanteDescripcion': MockParticipanteDescripcion
            };
            createController = function() {
                $injector.get('$controller')("ParticipanteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cumminsApp:participanteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
