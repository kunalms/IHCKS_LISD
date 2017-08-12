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
		}

		function index(){
			echo "api call";
		}

		function insert_user(){
			echo "api call input_user";
			$username=$this->input->get('username');
			echo $username;
			$this->load->model('lisd_model');
			echo "api call input_user";
		}
	}

 ?>