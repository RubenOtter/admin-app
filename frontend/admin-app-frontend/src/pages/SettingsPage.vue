<template>
  <div id="container">
    <div id="base">
      <div id="base-nav" class="tabs">
        <ul class="tabs-list">
          <li ref="websiteBtn" v-on:click.prevent="setTab" data-tab="website" class="active"><a data-tab="website" href="#">{{text.SetPage_WebsiteSettings}}</a></li>
          <li ref="restaurantBtn" v-on:click.prevent="setTab" data-tab="restaurant"><a data-tab="restaurant" href="#">{{text.SetPage_RestaurantSettings}}</a></li>
        </ul>
      </div>
      <div id="base-content">
        <div ref="websiteTab" class="tab active">
          <WebsiteSettings/>
        </div>
        <div ref="restaurantTab" class="tab">
          <RestaurantSettings/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import WebsiteSettings from "../components/settings_components/WebsiteSettings.vue"
import RestaurantSettings from "../components/settings_components/RestaurantSettings.vue"
import LanguageUtil from '../utils/LanguageUtil';


export default{
    data() {
        return {
          text: LanguageUtil.getTextObject(),
        }
    },
    components:{
        WebsiteSettings,
        RestaurantSettings
    },
    methods: {
        setTab(e){
            
            e.preventDefault();

            switch(e.path[0].getAttribute("data-tab"))
            {
                case 'website':
                    this.$refs.websiteBtn.classList.add('active')
                    this.$refs.websiteTab.classList.add('active')
                    this.$refs.websiteTab.style.display = 'block !important'

                    this.$refs.restaurantBtn.classList.remove('active')
                    this.$refs.restaurantTab.classList.remove('active')
                    this.$refs.restaurantTab.style.display = 'none !important';
                    break;

                case 'restaurant':
                    this.$refs.restaurantBtn.classList.add('active')
                    this.$refs.restaurantTab.classList.add('active')
                    this.$refs.restaurantTab.style.display = 'block !important'

                    this.$refs.websiteBtn.classList.remove('active')
                    this.$refs.websiteTab.classList.remove('active')
                    this.$refs.websiteTab.style.display = 'none !important'
                    break;
                
                default: break;
            }
        }
    },
}
</script>

<style scoped>
#container {
  top: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  height: fit-content;
  width: fit-content;
  text-align: left;
  background-color: var(--secondary-color);
}

#base {
  min-width: 65vw;
  min-height: 250px;
}

#base-nav {
  width: 100%;
  background-color: var(--primary-color);
  display: inline-block !important;
}

#base-content {
    width: 100%;
    display: inline-block !important;
}

/* tab list item */
.tabs .tabs-list {
  list-style: none;
  margin: 0px;
  padding: 0px;
}
.tabs .tabs-list li {
  width: 210px;
  height: 5px;
  float: left;
  padding-top: 15px;
  padding-bottom: 25px;
  text-align: center;
  background-color: transparent;
}
.tabs .tabs-list li:hover {
  cursor: pointer;
  background-color: #c4c4c426;
}
.tabs .tabs-list li a {
  text-decoration: none;
  color: var(--secondary-color);
  font-size: 1.2rem;
}


/* Tab content section */
#base-content .tab{
    display:none;
    width:95%;
    height:auto;
    border-radius:3px;
    padding:20px 15px;
    clear:both;
}
.tabs .tab h3{
    border-bottom:3px solid cornflowerblue;
    letter-spacing:1px;
    font-weight:normal;
    padding:5px;
}
.tabs .tab p{
    line-height:20px;
    letter-spacing: 1px;
}

/* When active state */
.active{
    display:block !important;
}
.tabs .tabs-list li.active{
    background-color: rgba(88, 88, 88, 0.39) !important;
    color:var(--secondary-color) !important;
}
.active a{
    color:var(--secondary-color) !important;
}

/* media query */
@media screen and (max-width:360px){
    .tabs{
        margin:0;
        width:96%;
    }
    .tabs .tabs-list li{
        width:80px;
    }
}
</style>