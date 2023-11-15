<template>
   <div>
      <section class="sign-in-page">
         <div class="container">
            <div class="row justify-content-center align-items-center height-self-center">
               <div class="col-md-6 col-sm-12 col-12 align-self-center">
                  <div class="sign-user_card ">
                     <div class="d-flex justify-content-center">
                        <div class="sign-user_logo">
                           <img src="@/assets/images/login/user.png" class=" img-fluid" alt="Logo">
                        </div>
                     </div>
                     <div class="sign-in-page-data">
                        <div class="sign-in-from w-100 pt-5 m-auto">
                           <h1 class="mb-3 text-center">Bienvenido!</h1>
                           <form class="mt-4">
                              <div class="form-group">
                                 <label for="exampleInputEmail2">Usuario</label>
                                 <input type="email" class="form-control mb-0" id="exampleInputEmail2"
                                    placeholder="Enter email">
                              </div>
                              <div class="form-group">
                                 <label for="exampleInputPassword2">Contraseña</label>
                                 <input type="password" class="form-control mb-0" id="exampleInputPassword2"
                                    placeholder="Password">
                              </div>
                              <div class="sign-info mt-4">
                                 <button type="submit" class="btn btn-primary mb-2">Iniciar sesión</button>
                              </div>

                           </form>
                        </div>
                     </div>
                     <div class="mt-2">
                        <div class="d-flex justify-content-center links">
                           <a href="pages-recoverpw.html">¿Olvistaste tu contraseña?</a>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </section>
   </div>
</template>
  
<script>

export default {
   name: 'App',
   components: {

   }
}
</script>
  
<style>

</style>
  