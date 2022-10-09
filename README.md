# Register Employee
	http://localhost:9010/employee/register
	
    example request that sent as JSON:
    {
        "email":"test@new-admin.com",
        "password":"test-new-admin",
        "name":"Ivan Ivanovich",
        "mobile":"0521234567",
        "role": {
                    "name":"ADMIN_ROLE"
                }
    }


# Authenticate Employee
    http://localhost:9010/employee/authenticate

    example request that sent as JSON:
	{
		"email":"test@admin.com",
		"password":"test-admin"
	}
    or
    {
        "email":"test@stock.com",
        "password":"test-stock"
    }
    or
    {
        "email":"test@order.com",
        "password":"test-order"
    }

# Find Employee By Id
	http://localhost:9010/employee/find-employee-by-id/{id}

    example:
    http://localhost:9010/employee/find-employee-by-id/5

# Find All Employees
	http://localhost:9010/employee/find-all-employees

# Delete Employee
	http://localhost:9010/employee/delete-employee-by-id/{id}

    example:
    http://localhost:9010/employee/delete-employee-by-id/7
