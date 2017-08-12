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
			echo "api call"
		}

		function insert_user{
			
			$username=$this->input->post('username');
			echo $username;
			$this->load->model('lisd_model');
		}
	}

 ?>