'use strict';

describe('Controller Tests', function() {

    describe('ParticipanteDescripcion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockParticipanteDescripcion, MockParticipante;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockParticipanteDescripcion = jasmine.createSpy('MockParticipanteDescripcion');
            MockParticipante = jasmine.createSpy('MockParticipante');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ParticipanteDescripcion': MockParticipanteDescripcion,
                'Participante': MockParticipante
            };
            createController = function() {
                $injector.get('$controller')("ParticipanteDescripcionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cumminsApp:participanteDescripcionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
