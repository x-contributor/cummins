'use strict';

describe('Controller Tests', function() {

    describe('PreguntaSeccion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreguntaSeccion, MockPregunta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreguntaSeccion = jasmine.createSpy('MockPreguntaSeccion');
            MockPregunta = jasmine.createSpy('MockPregunta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PreguntaSeccion': MockPreguntaSeccion,
                'Pregunta': MockPregunta
            };
            createController = function() {
                $injector.get('$controller')("PreguntaSeccionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cumminsApp:preguntaSeccionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
