<?php
defined('BASEPATH') OR exit('No direct script access allowed');

	/**
	* 
	*/
	class Api extends CI_Controller
	{
		
		function __construct()
		{
			parent::__construct();
			$this->load->model('lisd_model');
		}

		function index(){
			echo "api call";
		}

		function validate_user(){

			//$username=$this->input->get('_USER_NAME');
			//$password=$this->input->get('_PASSWORD');
			$inp=$this->input->get('_POST_LOGIN_SCREEN');
			//echo $username;
			//echo $password;
			//$this->lisd_model->fetch_users();
			echo $inp;


		}



		function insert_user(){
			echo "api call input_user";
			$username=$this->input->get('username');
			echo $username;
		
			echo "api call input_user";
		}
	}

 ?>