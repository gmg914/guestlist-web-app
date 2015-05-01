var guestListApp = angular.module('guestListApp', ['ngResource', 'ngDialog']);

guestListApp.controller('EventGuestsController', function($scope, $resource, $http, $timeout) {
	console.log("In EventGuestsController");
	
	$scope.initializeGuests = function() {
		console.log("In initializeGuests");
		
		$http.get('/guestlist/allguests').success(function(data) {
	  		$scope.guests = data;
		});
	};
	
	$scope.addGuest = function() {
		console.log("In addGuest");
		
		console.log("Current Guests");
		console.log($scope.guests);
				
		$http.post('/guestlist/guest/', $scope.guest)
        	.success(function(data) {
        		console.log("Post data");
                console.log(data);
        		$scope.guests = data;
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
	};

	//initialize guests
	$scope.initializeGuests();
});

guestListApp.controller('EventController', function($scope, $http) {    

    $scope.createEvent = function() {
        $http.post('/event/', $scope.event)
            .success(function(data) {
                $scope.event = data;
                $scope.eventCreated = true;                
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
    };
    
    $scope.updateSubEvents = function() {
        console.log("In updateSubEvents");
        
        var myUrl = '/event/subevent/' + $scope.event.eventKey;
        $http.get(myUrl).success(function(data) {
            $scope.subevents = data;
        });
    };

    $scope.addSubEvent = function() {
    
        $scope.subEvent.parentEvent = $scope.event.eventKey;

        $http.post('/event/subevent/', $scope.subEvent)
            .success(function(data) {
                $scope.subEvent = data;
                $scope.updateSubEvents();
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
    };

    $scope.addGuest = function() {
        $scope.guest.eventKey = $scope.event.eventKey.replace(/\s+/g, '');
        console.log($scope.guest.eventKey);                

        $http.post('/guestlist/guest/', $scope.guest)
            .success(function(data) {
                $scope.guests = data;
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
    };
});

guestListApp.controller('MyEventsController', function($scope, $http, ngDialog) {
    $scope.user = 'gmoy';    


    $scope.newEventPopUp = function () {
            ngDialog.open({ template: 'newEventPopUp.html', controller: 'MyEventsController' });
    };

    $scope.createEvent = function () {
        console.log("In MyEventsController createEvent()");
        console.log($scope.newEvent.eventName);
        console.log($scope.newEvent.eventDate);

        $scope.newEvent.user = $scope.user;

        console.log($scope.newEvent);
        $http.post('/event/', $scope.newEvent)
            .success(function(data) {
                console.log("Success!");  
                ngDialog.close();           
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
    };
});