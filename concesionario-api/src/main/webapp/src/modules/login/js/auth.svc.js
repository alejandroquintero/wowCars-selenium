(function (ng) {

    var mod = ng.module('authModule');

    mod.provider('authService', function () {

        //Default
        var values = {
            apiUrl: 'api/users/',
            loginState: 'login',
            logoutRedirectState: 'login',
            registerState: 'register',
            forgotPassState: 'forgot',
            successState: 'home',
            forbiddenState: 'forbidden',
            loginURL: 'login',
            registerURL: 'register',
            logoutURL: 'logout',
            forgotPassURL: 'forgot',
            meURL: 'me'
        };

        //Default Roles
        var roles = {admin:{}};
        
        this.setValues = function (newValues) {
            values = ng.extend(values, newValues);
        };

        this.getValues = function () {
            return values;
        };

        this.getRoles = function(){
            return roles;
        };

        this.setRoles = function(newRoles){
            roles = newRoles;
        };

        this.$get = ['$state', '$http','$rootScope','$cookies','Restangular', function ($state, $http, $rootScope,$cookies,Restangular) {
            return {
                getRoles: function(){
                    return roles;
                },
                login: function (user) {
                    return $http.post(values.apiUrl+values.loginURL, user).then(function (response) {
                        $rootScope.$broadcast('logged-in', response.data);        
                        $cookies.put("id_token",response.headers("id_token"));
                        $cookies.put("username",user.userName);
                        var permissions = JSON.stringify(response.data.permissions);
                        $cookies.put("permissions",permissions);
                        $state.go(values.successState);
                      
                    });
                },
                getConf: function () {
                    return values;
                },
                logout: function () {
                    
                    return $http.get(values.apiUrl+values.logoutURL).then(function () {
                        $rootScope.$broadcast('logged-out');
                        $cookies.remove("id_token");
                        $cookies.remove("username");
                        $cookies.remove("permissions");
                        $state.go(values.logoutRedirectState);
                    });
                },
                register: function (user) {
                    
                    return $http.post(values.apiUrl+values.registerURL, user).then(function (data) {
                        $state.go(values.loginState);
                    });
                },
                forgotPass: function (user) {
                    return $http.post(values.apiUrl+values.forgotPassURL, user).then(function (data) {
                        $state.go(values.loginState);
                    });
                },
                registration: function () {
                    $state.go(values.registerState);
                },
                goToLogin: function () {
                    $state.go(values.loginState);
                },
                goToForgotPass: function(){
                    $state.go(values.forgotPassState);
                },
                goToBack: function () {
                    $state.go(values.loginState);
                },
                goToSuccess: function () {
                    
                    $state.go(values.successState);
                },
                goToForbidden: function(){
                    $state.go(values.forbiddenState);
                },
                userAuthenticated: function(){
                    $http.get(values.apiUrl + values.meURL).then(function(response){
                       var permissions = JSON.stringify(response.data.permissions);
                        $cookies.put("permissions",permissions);
                   });
                    return $http.get(values.apiUrl + values.meURL);
                }
            };
        }];
    });
})(window.angular);

